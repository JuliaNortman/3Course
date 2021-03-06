import { City } from './city';
import { CrewMember } from './crew-member';

export class Flight {
    id: number;
    departCity: City;
    departTime: Date;
    destCity: City;
    destTime: Date;
    crewMembers: CrewMember[];

    constructor() {
        this.departCity = new City();
        this.destCity = new City();
    }
}
