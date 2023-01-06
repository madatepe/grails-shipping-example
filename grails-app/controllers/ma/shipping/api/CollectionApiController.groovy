package ma.shipping.api

import grails.rest.RestfulController

class CollectionApiController extends RestfulController {

    def collectionApiService

    def show() {
        render(contentType: 'text/json') {
            collectionApiService.show(params)
        }
    }

    def save() {
        render(contentType: 'text/json') {
            collectionApiService.save(request.JSON)
        }
    }

    def getCompanyCollections() {
        render(contentType: 'text/json') {
            collectionApiService.getCompanyCollections(params)
        }
    }
}
