package ma.shipping.api

import grails.rest.RestfulController

class LookUpApiController extends RestfulController {

    def lookUpApiService

    def show() {
        render(contentType: 'text/json') {
            lookUpApiService.show(params)
        }
    }
}
