/**
 * ComonlWeb
 * API per ComonlWeb
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { ApiError } from './apiError';
import { Delega } from './delega';


export interface PostDelegaResponse { 
    apiErrors?: Array<ApiError>;
    apiWarnings?: Array<ApiError>;
    delega?: Delega;
}
