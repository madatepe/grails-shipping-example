package ma.shipping.api

import grails.rest.RestfulController

class ReportApiController extends RestfulController {

    def reportApiService

    def companyShippings() {
        render(contentType: 'text/json') {
            reportApiService.companyShippings(request.JSON)
        }
    }

    def companyReportGeneralInformations() {
        render(contentType: 'text/json') {
            reportApiService.companyReportGeneralInformations(params)
        }
    }

    def vehicleShippings() {
        render(contentType: 'text/json') {
            reportApiService.vehicleShippings(request.JSON)
        }
    }

    def vehicleReportGeneralInformations() {
        render(contentType: 'text/json') {
            reportApiService.vehicleReportGeneralInformations(params)
        }
    }

    def myReports() {
        render(contentType: 'text/json') {
            reportApiService.myReports(params)
        }
    }
}
