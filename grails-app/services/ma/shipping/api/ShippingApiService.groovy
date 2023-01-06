package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Shipping
import ma.shipping.api.Invoice
import ma.shipping.api.Company

import java.text.SimpleDateFormat

@Transactional
class ShippingApiService {

    def show(Map parameters) {
        def result
        List shippings = []

        if (parameters.id) {
            Shipping shipping = Shipping.read(parameters.id)
            shippings.add(shipping)
        } else {
            shippings = Shipping.list().reverse()
        }

        result = shippings.collect {
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

        if (parameters.id) {
            return result[0]
        } else {
            return result
        }
    }

    def save(Map parameters) {
        Invoice vehicleInvoice
        Invoice companyInvoice
        Shipping shipping = Shipping.get(parameters.id?.toInteger())
        if (shipping) shipping.invoices?.clear()

        parameters.date = new SimpleDateFormat("yyyy-MM-dd").parse(parameters.date)

        if (parameters.vehicleInvoiceNo) {
            vehicleInvoice  = new Invoice(
                    date: parameters.date,
                    number: parameters.vehicleInvoiceNo,
                    type: false,
                    baseAmount: parameters.debtBaseAmount,
                    taxAmount: parameters.debtTaxAmount,
                    totalAmount: parameters.debtTotalAmount,
                    vehicle: parameters.vehicle ? [id: parameters.vehicle.id] : null,
            );

            vehicleInvoice.save(flush: true, failOnError: true);
        }

        if (parameters.companyInvoiceNo) {
            companyInvoice  = new Invoice(
                    date: parameters.date,
                    number: parameters.companyInvoiceNo,
                    type: true,
                    baseAmount: parameters.creditBaseAmount,
                    taxAmount: parameters.creditTaxAmount,
                    totalAmount: parameters.creditTotalAmount,
                    company: parameters.company ? [id: parameters.company.id] : null,
            );

            companyInvoice.save(flush: true, failOnError: true);
        }

        if (!shipping) {
            shipping = new Shipping(
                    startLocation: parameters.startLocation,
                    endLocation: parameters.endLocation,
                    amount: parameters.creditTotalAmount,
                    date: parameters.date,
                    tonnage: parameters.tonnage?.text,
                    company: parameters.company ? [id: parameters.company.id] : null,
                    vehicle: parameters.vehicle ? [id: parameters.vehicle.id] : null,
                    driver: parameters.driver ? [id: parameters.driver.id] : null,
                    debtBaseAmount: parameters.debtBaseAmount,
                    debtTaxAmount: parameters.debtTaxAmount,
                    debtTotalAmount: parameters.debtTotalAmount,
                    debtAdvancePayment: parameters.debtAdvancePayment,
                    debtFuelPayment: parameters.debtFuelPayment,
            );
        }

        shipping.properties = parameters
        if (vehicleInvoice) shipping.addToInvoices(vehicleInvoice)
        if (companyInvoice) shipping.addToInvoices(companyInvoice)

        shipping.save(flush: true)

//        shipping.invoices.addAll([vehicleInvoice, companyInvoice])
//        shipping.add(vehicleInvoice)
//        shipping.add(companyInvoice)

        return [message: 'Sevkiyat başarıyla kaydedilmiştir.']
    }
}
