package ma.shipping.api

import grails.rest.RestfulController

class InvoiceApiController extends RestfulController {

    def invoiceApiService

    def show() {
        render(contentType: 'text/json') {
            invoiceApiService.show(params)
        }
    }

    def save() {
        render(contentType: 'text/json') {
            invoiceApiService.save(request.JSON)
        }
    }

    def bulkInvoicingShippings() {
        render(contentType: 'text/json') {
            invoiceApiService.bulkInvoicingShippings(request.JSON)
        }
    }
}
