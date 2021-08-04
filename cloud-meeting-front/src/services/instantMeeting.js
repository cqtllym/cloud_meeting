import {INSTANT} from '@/services/api'
import {request, METHOD} from '@/utils/request'
import qs from 'qs'

/**
 * 发起临时会议服务
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function InitInstantMeeting(data) {
    return request(INSTANT, METHOD.POST,qs.stringify(data),
      {
        headers:{
          'Content-Type':'application/x-www-form-urlencoded'
        },
      })
  }
  
  export default {
    InitInstantMeeting
  }
  