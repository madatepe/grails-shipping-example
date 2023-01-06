package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Shipping
import ma.shipping.api.Invoice
import ma.shipping.api.Company
import ma.shipping.api.Vehicle
import ma.shipping.api.Collection

@Transactional
class ReportApiService {
    def companyShippings(Map parameters) {
        if (parameters.company) {
            def company = Company.get(parameters.company.id)
            List companyShippings = Shipping.findAllByCompany(company).reverse()

            def collectedCompanyShippings = companyShippings.collect {
                return [
                        id: it.id,
                        date: it.date,
                        dateCreated: it.dateCreated,
                        startLocation: it.startLocation,
                        endLocation: it.endLocation,
                        tonnage: it.tonnage,
                        creditBaseAmount: it.creditBaseAmount,
                        creditTaxAmount: it.creditTaxAmount,
                        creditTotalAmount: it.creditTotalAmount,
                        creditKilogram: it.creditKilogram,
                        creditUnitPrice: it.creditUnitPrice,
                        debtBaseAmount: it.debtBaseAmount,
                        debtTaxAmount: it.debtTaxAmount,
                        debtTotalAmount: it.debtTotalAmount,
                        debtAdvancePayment: it.debtAdvancePayment,
                        debtFuelPayment: it.debtFuelPayment,
                        debtBalance: it.debtBalance,
                        invoices: it.invoices.collect { Invoice invoice ->
                            return [
                                    id: invoice.id,
                                    type: invoice.type,
                                    number: invoice.number,
                            ]
                        },
                        company: it.company ? [
                                id: it.company.id,
                                name: it.company.name,
                                address: it.company.address,
                                phone: it.company.phone,
                                mobile: it.company.mobile,
                                representative: it.company.representative,
                        ] : [:],
                        vehicle: it.vehicle ? [
                                id: it.vehicle.id,
                                owner: it.vehicle.owner,
                                type: it.vehicle.type,
                                description: it.vehicle.description,
                                licensePlate: it.vehicle.licensePlate,
                                licensePlate2: it.vehicle.licensePlate2,
                                driver: [
                                        id: it.driver.id,
                                        name: it.driver.name,
                                        mobile: it.driver.mobile
                                ]
                        ] : [:]
                ]
            }

            return [data: collectedCompanyShippings]
        } else {
            return [error: "Hatalı parametre"]
        }
    }

    def companyReportGeneralInformations(Map parameters) {
        if (parameters.companyId) {
            def company = Company.get(parameters.companyId)
            List companyShippings = Shipping.findAllByCompany(company)
            List companyCollections = Collection.findAllByCompany(company)

            Double calculatedTotalCredit = 0
            Double calculatedGeneralTotalDebt = 0
            Double calculatedGeneralTotalCredit = 0
            Double calculatedBalance = 0

            companyShippings.each { companyShipping ->
                calculatedGeneralTotalCredit += companyShipping.creditTotalAmount

                if (companyShipping.invoices) {
                    companyShipping.invoices.each { companyInvoice ->
                        if (companyInvoice.type == true) {
                            calculatedTotalCredit += companyShipping.creditTotalAmount
                        }
                    }
                }
            }

            companyCollections.each { companyCollection ->
                calculatedGeneralTotalDebt += companyCollection.amount
            }

            calculatedBalance = calculatedTotalCredit - calculatedGeneralTotalDebt

            return [
                    generalTotalCredit: calculatedGeneralTotalCredit,
                    totalCredit: calculatedTotalCredit,
                    totalDebt: calculatedGeneralTotalDebt,
                    totalBalance: calculatedBalance,
            ]
        } else {
            return [error: "Hatalı parametre"]
        }
    }

    def vehicleShippings(Map parameters) {
        if (parameters.vehicle) {
            def vehicle = Vehicle.get(parameters.vehicle.id)
            List vehicleShippings = Shipping.findAllByVehicle(vehicle).reverse()

            def collectedVehicleShippings = vehicleShippings.collect {
                return [
                        id: it.id,
                        date: it.date,
                        dateCreated: it.dateCreated,
                        startLocation: it.startLocation,
                        endLocation: it.endLocation,
                        tonnage: it.tonnage,
                        creditBaseAmount: it.creditBaseAmount,
                        creditTaxAmount: it.creditTaxAmount,
                        creditTotalAmount: it.creditTotalAmount,
                        creditKilogram: it.creditKilogram,
                        creditUnitPrice: it.creditUnitPrice,
                        debtBaseAmount: it.debtBaseAmount,
                        debtTaxAmount: it.debtTaxAmount,
                        debtTotalAmount: it.debtTotalAmount,
                        debtAdvancePayment: it.debtAdvancePayment,
                        debtFuelPayment: it.debtFuelPayment,
                        debtBalance: it.debtBalance,
                        invoices: it.invoices.collect { Invoice invoice ->
                            return [
                                    id: invoice.id,
                                    type: invoice.type,
                                    number: invoice.number,
                            ]
                        },
                        company: it.company ? [
                                id: it.company.id,
                                name: it.company.name,
                                address: it.company.address,
                                phone: it.company.phone,
                                mobile: it.company.mobile,
                                representative: it.company.representative,
                        ] : [:],
                        vehicle: it.vehicle ? [
                                id: it.vehicle.id,
                                owner: it.vehicle.owner,
                                type: it.vehicle.type,
                                description: it.vehicle.description,
                                licensePlate: it.vehicle.licensePlate,
                                licensePlate2: it.vehicle.licensePlate2,
                                driver: [
                                        id: it.driver.id,
                                        name: it.driver.name,
                                        mobile: it.driver.mobile
                                ]
                        ] : [:]
                ]
            }

            return [data: collectedVehicleShippings]
        } else {
            return [error: "Hatalı parametre"]
        }
    }

    def vehicleReportGeneralInformations(Map parameters) {
        if (parameters.vehicleId) {
            def vehicle = Vehicle.get(parameters.vehicleId)
            List vehicleShippings = Shipping.findAllByVehicle(vehicle)

            Double calculatedGeneralTotalDebt = 0
            Double calculatedGeneralDebtAdvancePayment = 0
            Double calculatedGeneralDebtFuelPayment = 0
            Double calculatedGeneralDebtBalance = 0

            vehicleShippings?.each { vehicleShipping ->

                calculatedGeneralTotalDebt += vehicleShipping.debtTotalAmount ?: 0
                calculatedGeneralDebtAdvancePayment += vehicleShipping.debtAdvancePayment ?: 0
                calculatedGeneralDebtFuelPayment += vehicleShipping.debtFuelPayment ?: 0
                calculatedGeneralDebtBalance += vehicleShipping.debtBalance ?: 0
            }

            return [
                    generalTotalDebt: calculatedGeneralTotalDebt,
                    generalDebtAdvancePayment: calculatedGeneralDebtAdvancePayment,
                    generalDebtFuelPayment: calculatedGeneralDebtFuelPayment,
                    generalDebtBalance: calculatedGeneralDebtBalance,
            ]
        } else {
            return [error: "Hatalı parametre"]
        }
    }

    def myReports(Map parameters) {
        if (parameters.periodDate) {
            def periodDate = Date.parse("yyyy-MM-dd", parameters.periodDate)
            Double calculatedTotalDebt = 0
            Double calculatedTotalCredit = 0
            Double calculatedGeneralTotalDebt = 0
            Double calculatedGeneralTotalCredit = 0
            Double calculatedGeneralDebtAdvancePayment = 0
            Double calculatedGeneralDebtFuelPayment = 0
            Double calculatedGeneralDebtBalance = 0

//            String hql = "FROM Shipping s JOIN s.invoices si " +
//                    "WHERE si.company is NOT NULL " +
//                    "AND si.date <= :periodDate"
//            List result = Shipping.executeQuery(hql, [periodDate: periodDate])
//            return [data: result]

            List allshippingsInPeriod = Shipping.findAllByDateBetween(periodDate, new Date())
            allshippingsInPeriod.each { shipping ->
                calculatedGeneralTotalCredit += shipping.creditTotalAmount ?: 0
                calculatedGeneralTotalDebt += shipping.debtTotalAmount ?: 0
                calculatedGeneralDebtAdvancePayment += shipping.debtAdvancePayment ?: 0
                calculatedGeneralDebtFuelPayment += shipping.debtFuelPayment ?: 0
                calculatedGeneralDebtBalance += shipping.debtBalance ?: 0

                if (shipping.invoices) {
                    shipping.invoices.each { invoice ->
                        if (invoice.type == true) {
                            calculatedTotalCredit += invoice.totalAmount
                        } else if (invoice.type == false) {
                            calculatedTotalDebt += invoice.totalAmount
                        }
                    }
                }
            }

            return [
                    calculatedTotalCredit: calculatedTotalCredit,
                    calculatedTotalDebt: calculatedTotalDebt,
                    calculatedGeneralTotalCredit: calculatedGeneralTotalCredit,
                    calculatedGeneralTotalDebt: calculatedGeneralTotalDebt,
                    calculatedGeneralDebtAdvancePayment: calculatedGeneralDebtAdvancePayment,
                    calculatedGeneralDebtFuelPayment: calculatedGeneralDebtFuelPayment,
                    calculatedGeneralDebtBalance: calculatedGeneralDebtBalance,
            ]
        } else {
            return [error: "Hatalı parametre"]
        }
    }
}
