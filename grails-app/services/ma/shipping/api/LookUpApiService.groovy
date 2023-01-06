package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Vehicle
import ma.shipping.api.Driver
import ma.shipping.api.Company

@Transactional
class LookUpApiService {

    def show (Map parameters) {
        def result
        String domainName = parameters.domain

        switch (domainName) {
            case "Company":
                List companies = Company.list().collect {
                    return [
                            id: it.id,
                            name: it.name,
                    ]
                }
                result = companies
                break
            case "Vehicle":
                List vehicles = Vehicle.list().collect {
                    return [
                            id: it.id,
                            licensePlate: it.licensePlate,
                            owner: it.owner
                    ]
                }
                result = vehicles
                break
            case "Driver":
                if (parameters.refId) {
                    Vehicle vehicle = Vehicle.read(parameters.refId)
                    result = vehicle.drivers
                } else {
                    List drivers = Driver.list()
                    result = drivers
                }
                break
            default:
                result = [data: 'Name parameters error for lookup']
                break
        }

        return result
    }
}
