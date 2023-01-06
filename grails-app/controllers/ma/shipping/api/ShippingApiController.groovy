package ma.shipping.api

import grails.rest.RestfulController

class ShippingApiController extends RestfulController {

    def shippingApiService

    def show() {
        render(contentType: 'text/json') {
            shippingApiService.show(params)
        }
    }

    def save() {
        render(contentType: 'text/json') {
            shippingApiService.save(request.JSON)
        }
    }
}
