import {request,METHOD} from "@/utils/request";
import {CONFIRM_MEETINGPASSWORD,ADD_MEETING} from "@/services/api"
import qs from 'qs'

/**
 * 判断密码是否正确
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function ConfirmMpwd(meetingNumber,meetingPassword){
    console.log(meetingNumber,meetingPassword)
    return request(CONFIRM_MEETINGPASSWORD,METHOD.GET,{
        meetingNumber:meetingNumber,
        meetingPassword:meetingPassword,
    })
}

/**
 * 判断密码是否正确，以加入会议
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function AddMeeting(meetingNumber,requirePassword,meetingPassword){
    return request(ADD_MEETING,METHOD.PUT,qs.stringify({
        meetingNumber:meetingNumber,
        requirePassword:requirePassword,
        meetingPassword:meetingPassword,
    }),
    {
        headers:{
          'Content-Type':'application/x-www-form-urlencoded'
        },
    })
}

export default {
    ConfirmMpwd,

}