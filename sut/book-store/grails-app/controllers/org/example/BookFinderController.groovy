package org.example

class BookFinderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bookFinderInstanceList: BookFinder.list(params), booksFoundTotal: 0]
    }

    def create = {
        def bookFinderInstance = new BookFinder()
        bookFinderInstance.properties = params
        return [bookFinderInstance: bookFinderInstance]
    }

    def search = {
        def bookFinderInstance = new BookFinder(params)
        def booksFound = Book.find("from Book as b where b.author=:author",
                [author: bookFinderInstance.searchPhrase],
                [cache: true])

        render(view: "list", model: [booksFound: booksFound, booksFoundTotal: booksFound.count()])
    }


    def save = {
        def bookFinderInstance = new BookFinder(params)
        if (bookFinderInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), bookFinderInstance.id])}"
            redirect(action: "show", id: bookFinderInstance.id)
        }
        else {
            render(view: "create", model: [bookFinderInstance: bookFinderInstance])
        }
    }

    def show = {
        def bookFinderInstance = BookFinder.get(params.id)
        if (!bookFinderInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), params.id])}"
            redirect(action: "list")
        }
        else {
            [bookFinderInstance: bookFinderInstance]
        }
    }

    def edit = {
        def bookFinderInstance = BookFinder.get(params.id)
        if (!bookFinderInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [bookFinderInstance: bookFinderInstance]
        }
    }

    def update = {
        def bookFinderInstance = BookFinder.get(params.id)
        if (bookFinderInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (bookFinderInstance.version > version) {
                    
                    bookFinderInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bookFinder.label', default: 'BookFinder')] as Object[], "Another user has updated this BookFinder while you were editing")
                    render(view: "edit", model: [bookFinderInstance: bookFinderInstance])
                    return
                }
            }
            bookFinderInstance.properties = params
            if (!bookFinderInstance.hasErrors() && bookFinderInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), bookFinderInstance.id])}"
                redirect(action: "show", id: bookFinderInstance.id)
            }
            else {
                render(view: "edit", model: [bookFinderInstance: bookFinderInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def bookFinderInstance = BookFinder.get(params.id)
        if (bookFinderInstance) {
            try {
                bookFinderInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bookFinder.label', default: 'BookFinder'), params.id])}"
            redirect(action: "list")
        }
    }
}
