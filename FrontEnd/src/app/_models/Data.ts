export interface Vacation {
  id?: number;
  destination: string;
  startDate: Date;
  endDate: Date;
  price: number;
  availability: number;
  boardingLocation?: string;
  shipName?: string;
  hotelName?: string;
  attractions?: Attraction[];
  guide?: string;
  itinerary?: string;
  dtype?: string;
}

export interface Attraction {
  id: number;
  name: string;
  needsTicket: boolean;
  ticketPrice?: boolean;
  schedule?: boolean;
}

export interface Client{
  id?: number;
  fullName: string;
  email: string;
  phoneNumber: string;
}

export interface Employee{
  id?: number;
  email: string;
  fullName: string;
  imageUrl?: string;
  password?: string;
}

export interface Reservation{
  id?: number;
  vacation: Vacation;
  client: Client;
  numberOfPeople: number;
  paymentStatus: string;
}

export interface ReservationStripped{
  vacationId: number;
  clientId: number;
  numberOfPeople: number;
  paymentStatus: string;
}

export interface ResponseString{
  message: string
}
