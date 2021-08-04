import {request,METHOD} from "@/utils/request";
//import qs from 'qs'
import {GET_MEETINGSTATE} from "@/services/api"
/**
 * 获取会议状态服务
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getM(meetingNumber,user_id){
    return request(GET_MEETINGSTATE,METHOD.GET,{
            meetingNumber: meetingNumber,
            user_id:user_id
        }
    )
}
export default {
    getM,
} 