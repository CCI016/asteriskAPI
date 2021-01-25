import { Provider } from "./provider";
import { Prompt } from "./prompt";

export interface Candy{
    id : number;
    name : string;
    prompt : Prompt;
    provider : Provider;
    isDeleted : boolean;
    numberOfPlays : number;
}