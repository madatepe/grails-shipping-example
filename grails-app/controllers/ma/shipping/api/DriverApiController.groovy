package ma.shipping.api

import grails.rest.RestfulController

class DriverApiController extends RestfulController {

    def driverApiService

    def show() {
        render(contentType: 'text/json') {
            driverApiService.show(params)
        }
    }

    def save() {
        render(contentType: 'text/json') {
            driverApiService.save(request.JSON)
        }
    }
}
