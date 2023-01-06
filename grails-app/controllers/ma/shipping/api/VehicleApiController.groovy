package ma.shipping.api

import grails.rest.RestfulController

class VehicleApiController extends RestfulController {

    def vehicleApiService

    def show() {
        render(contentType: 'text/json') {
            vehicleApiService.show(params)
        }
    }

    def save() {
        render(contentType: 'text/json') {
            vehicleApiService.save(request.JSON)
        }
    }
}
