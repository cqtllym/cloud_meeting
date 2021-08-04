import { LOGIN, LOGOUT, ROUTES, REGISTER, MODIFY_USER, USER_PASSWORD, DELETE_USER } from '@/services/api'
import { request, METHOD } from '@/utils/request'
import qs from 'qs'
/**
 * 注册服务
 * @param name 账户名
 * @param password 账户密码
 * @param telephone 手机号码
 * @param sex 性别
 * @param email 邮箱
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function register(name, password, telephone, sex, email) {
  return request(REGISTER, METHOD.POST, qs.stringify({
    name: name,
    password: password,
    telephone: telephone,
    sex: sex,
    email: email
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


/**
 * 登录服务
 * @param username 账户名
 * @param password 账户密码
 * @returns {Promise<AxiosResponse<T>>}
 */
export async function login(name, password) {
  return request(LOGIN, METHOD.POST, qs.stringify({
    name: name,
    password: password
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


export async function getRoutesConfig() {
  return request(ROUTES, METHOD.GET)
}

/**
 * 退出登录
 * @returns {Promise<AxiosResponse<T>>}
 */
export function logout() {
  return request(LOGOUT, METHOD.GET)
}

export default {
  login,
  logout,
  getRoutesConfig
}

/**
 * 更改用户个人信息
 * @param {用户ID} id 
 * @param {用户名} name 
 * @param {电话号码} telephone 
 * @param {性别} sex 
 * @param {邮箱} email 
 * @returns 
 */
export async function modifyUser(id, name, telephone, sex, email) {
  return request(MODIFY_USER, METHOD.PUT, qs.stringify({
    id: id,
    name: name,
    telephone: telephone,
    sex: sex,
    email: email
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}


/**
 * 更改用户密码
 * @param {用户ID} id 
 * @param {旧密码} oldPsd 
 * @param {新密码} newPsd 
 * @returns 
 */
export async function userPassword(id, oldPsd, newPsd,) {
  return request(USER_PASSWORD, METHOD.PUT, qs.stringify({
    userId: id,
    oldPassword: oldPsd,
    newPassword: newPsd,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}

/**
 * 注销用户
 * @param {用户ID} userId 
 * @returns
 */
 export async function deleteUser(id) {
  return request(DELETE_USER, METHOD.POST, qs.stringify({
    userId: id,
  }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
}