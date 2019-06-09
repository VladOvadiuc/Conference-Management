import {User} from "../../users/shared/user.model";

export class Session {
  id: number;
  title: string;
  location: string;
  when: string;
  supervisorId: number;
  participantsNr: number;
  maxParticipants: number;
  listeners: User[];
  speakers: User[];
}
