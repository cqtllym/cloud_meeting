import {request, METHOD} from '@/utils/request'
import { MY ,COMMON_RESERVATION,ROOM_RESERVATION} from '@/services/api'

/**
 * 获取我的会议服务
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function getMyMeetingList() {
    return request(MY, METHOD.GET)
  }
  
/**
 * 取消会议室服务
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function cancelRoomReservation(roomMeetingId) {
    return request(ROOM_RESERVATION, METHOD.DELETE,{
      id:roomMeetingId
    })
  }

/**
 * 取消会议服务
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function cancelCommonReservation(meetingId,userId) {
    return request(COMMON_RESERVATION, METHOD.DELETE,{
      meetingId:meetingId,
      userId:userId
    })
  }
  

  export default {
    getMyMeetingList,
    cancelRoomReservation,
    cancelCommonReservation,

  }
  