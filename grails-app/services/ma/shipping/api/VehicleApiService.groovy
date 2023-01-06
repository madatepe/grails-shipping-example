package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Vehicle

@Transactional
class VehicleApiService {

    def show(Map parameters) {
        def result
        List vehicles = []

        if (parameters.id) {
            Vehicle vehicle = Vehicle.get(parameters.id)
            vehicles.add(vehicle)
        } else {
            vehicles = Vehicle.list().reverse()
        }

        result = vehicles.collect {
            return [
                    id              : it.id,
                    address         : it.address,
                    debt            : it.debt,
                    description     : it.description,
                    licensePlate    : it.licensePlate,
                    licensePlate2   : it.licensePlate2,
                    mobile          : it.mobile,
                    owner           : it.owner,
                    tcIdentityNumber: it.tcIdentityNumber,
                    type            : it.type,
                    drivers         : it.drivers.collect { Driver driver ->
                        return [
                                id    : driver.id,
                                tcIdentityNumber  : driver.tcIdentityNumber,
                                name  : driver.name,
                                mobile: driver.mobile
                        ]
                    }
            ]
        }

        if (parameters.id) {
            return result[0]
        } else {
            return [data: result]
        }
    }

    def save(Map parameters) {
        Vehicle vehicle = Vehicle.get(parameters.id?.toInteger())

        if (!vehicle) {
            vehicle = new Vehicle();
        }

        vehicle.properties = parameters
        vehicle.save(flush: true)

        return [data: vehicle]
    }
}
