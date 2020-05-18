export class Path {
    private readonly rootUrl = "http://localhost:8080/";
    
    getAllFlightsPath(): string {
        return this.rootUrl + "flight/all";
    }

    getFlightPath(id: number): string {
        return this.rootUrl + id.toString();
    }

    postFlightPath(): string{
        return this.rootUrl + "admin/add";
    }

    deleteFlightPath(id: number): string {
        return this.rootUrl + "admin/delete/" + id.toString();
    }

}