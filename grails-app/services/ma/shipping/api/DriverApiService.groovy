package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Driver

@Transactional
class DriverApiService {

    def show(Map parameters) {
        def result

        if (parameters.id) {
            Driver driver = Driver.read(parameters.id)

            result = driver
        } else {
            List data = Driver.list().collect {
                return [
                        id: it.id,
                        name: it.name,
                        mobile: it.mobile
                ]
            }

            result = [data: data]
        }

        return result
    }

    def save(Map parameters) {
        Driver driver = Driver.get(parameters.id?.toInteger())

        if (!driver) {
            driver = new Driver();
        }

        driver.properties = parameters
        driver.save(flush: true)

        return [data: driver]
    }
}
