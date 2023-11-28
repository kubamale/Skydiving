export interface SkydiverModel{
    id: number,
    firstName: string,
    lastName: string,
    weight: number,
    licence: string,
    jumpType: string,
    email: string,
}

export interface SkydiverInfoModel{
    email: string,
    firstName: string,
    lastName: string
}

export interface SkydiverApprovalModel{
    firstName: string,
    lastName: string,
    email: string,
    licence: string,
}