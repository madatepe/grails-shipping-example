package ma.shipping.api

import grails.rest.RestfulController

class CompanyApiController extends RestfulController {

    def companyApiService

    def show() {
        render(contentType: 'text/json') {
            companyApiService.show(params)
        }
    }

    def save() {
        render(contentType: 'text/json') {
            companyApiService.save(request.JSON)
        }
    }
}
