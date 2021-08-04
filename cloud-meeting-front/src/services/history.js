import { request, METHOD } from "@/utils/request";
import { HISTORY, MEETING_RESULT } from "@/services/api";
/**
 * 获取历史会议
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getHistoryMeetingList(start_time, end_time) {
  return request(HISTORY, METHOD.GET, {
    start_time: start_time,
    end_time: end_time
  })
}

/**
 * 获取预约会议室的会议记录
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getRoomRecord(type, room_meeting_id) {
  return request(MEETING_RESULT, METHOD.GET, {
    type: type,
    meeting_id: 0,
    room_meeting_id: room_meeting_id
  })
}

/**
 * 获取未使用会议室的会议记录
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function getRecord(type, meeting_id,) {
  return request(MEETING_RESULT, METHOD.GET, {
    type: type,
    meeting_id: meeting_id
  })
}

export default {
  getHistoryMeetingList,
  getRecord,
  getRoomRecord
}