import { CrewRole } from './crew-role';
import { Flight } from './flight';

export class CrewMember {
    id: number;
    name: string;
    role: CrewRole;
    flightes: Flight[];
}