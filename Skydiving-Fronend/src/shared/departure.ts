import { PlaneModel } from "./plane";
import { SkydiverModel } from "./skydiver";

export interface DepartureDetailsModel{
    id: number,
    date: string,
    time: string,
    skydiversAmount: number,
    studentsAmount: number,
    affAmount: number,
    allowStudents: boolean,
    allowAFF: boolean,
    plane: PlaneModel,
    totalWeight: number,
    skydivers: SkydiverModel[]
}


export interface DepartureUpdateModel{
    id: number,
    date: string,
    time: string,
    allowStudents: boolean,
    allowAFF: boolean,
    planeId: number,
    usersId: number[],
}