import Cookie from 'js-cookie'

//300拦截 身份验证失效
const resp300 = {
  onFulfilled(response, options) {
    const {message,router} = options
    if (response.data.code === 300) {
      message.error('身份验证已过期，请重新登录！')
      Cookie.remove('JSESSIONID')
      router.push('/login')
    }
    return response
  },
  onRejected(error, options) {
    const {message} = options
    const {response} = error
    if (response.status === 300) {
      message.error('请求被拒绝')
    }
    return Promise.reject(error)
  }
}


//401拦截 用户已注销
const respcode401 = {
  onFulfilled(response, options) {
    const {message,router} = options
    if (response.data.code === 401) {
      message.error('用户不存在或已注销，请重新登录！')
      Cookie.remove('JSESSIONID')
      router.push('/login')
    }
    return response
  },
  onRejected(error, options) {
    const {message} = options
    const {response} = error
    if (response.status === 300) {
      message.error('请求被拒绝')
    }
    return Promise.reject(error)
  }
}


// 401拦截
const resp401 = {
  /**
   * 响应数据之前做点什么
   * @param response 响应对象
   * @param options 应用配置 包含: {router, i18n, store, message}
   * @returns {*}
   */
  onFulfilled(response, options) {
    const {message} = options
    if (response.code === 401) {
      message.error('无此权限')
    }
    return response
  },
  /**
   * 响应出错时执行
   * @param error 错误对象
   * @param options 应用配置 包含: {router, i18n, store, message}
   * @returns {Promise<never>}
   */
  onRejected(error, options) {
    const {message} = options
    const {response} = error
    if (response.status === 401) {
      message.error('无此权限')
    }
    return Promise.reject(error)
  }
}

const resp403 = {
  onFulfilled(response, options) {
    const {message} = options
    if (response.code === 403) {
      message.error('请求被拒绝')
    }
    return response
  },
  onRejected(error, options) {
    const {message} = options
    const {response} = error
    if (response.status === 403) {
      message.error('请求被拒绝')
    }
    return Promise.reject(error)
  }
}

const reqCommon = {
  /**
   * 发送请求之前做些什么
   * @param config axios config
   * @param options 应用配置 包含: {router, i18n, store, message}
   * @returns {*}
   */
  onFulfilled(config, options) {
    const {message} = options
    const {url, xsrfCookieName} = config
    if ((url.indexOf('login') === -1 && url.indexOf('register') === -1)&& xsrfCookieName && !Cookie.get(xsrfCookieName)) {
      message.warning('身份认证已过期，请重新登录！')
    }

    return config
  },
  /**
   * 请求出错时做点什么
   * @param error 错误对象
   * @param options 应用配置 包含: {router, i18n, store, message}
   * @returns {Promise<never>}
   */
  onRejected(error, options) {
    const {message} = options
    message.error(error.message)
    return Promise.reject(error)
  }
}

export default {
  request: [reqCommon], // 请求拦截
  response: [resp401, resp403,resp300,respcode401] // 响应拦截
}
