package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Company

@Transactional
class CompanyApiService {

    def show(Map parameters) {
        def result

        if (parameters.id) {
            Company company = Company.read(parameters.id)

            result = company
        } else {
            List data = Company.list().reverse()

            result = [data: data]
        }

        return result
    }

    def save(Map parameters) {
        Company company = Company.get(parameters.id?.toInteger())

        if (!company) {
            company = new Company();
        }

        company.properties = parameters
        company.save(flush: true)

        return [data: company]
    }
}
