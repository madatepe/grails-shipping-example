package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Collection
import ma.shipping.api.Company

import java.text.SimpleDateFormat

@Transactional
class CollectionApiService {

    def show(Map parameters) {
        def result

        if (parameters.id) {
            Collection collection = Collection.read(parameters.id)

            result = collection
        } else {
            List data = Collection.list()

            result = [data: data]
        }

        return result
    }

    def save(Map parameters) {
        Collection collection = Collection.get(parameters.id?.toInteger())

        parameters.date = new SimpleDateFormat("yyyy-MM-dd").parse(parameters.date)

        if (!collection) {
            collection = new Collection();
        }

        collection.properties = parameters
        collection.save(flush: true)

        return [data: collection]
    }

    def getCompanyCollections(Map parameters) {
        def result

        if (parameters.id) {
            Company company = Company.read(parameters.id)
            List companyCollections = Collection.findAllByCompany(company)

            result = companyCollections
        } else {
            result = [error: "Hatalı parametre"]
        }

        return result
    }
}
