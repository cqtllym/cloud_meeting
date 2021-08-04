import TabsView from '@/layouts/tabs/TabsView'
import BlankView from '@/layouts/BlankView'
// import PageView from '@/layouts/PageView'

// 路由配置
const options = {
  routes: [
    {
      path: '/register',
      name: '注册页',
      component: () => import('@/pages/register/index.vue')
    },
    {
      path: '/login',
      name: '登录页',
      component: () => import('@/pages/login/Login.vue')
    },
    {
      path: '*',
      name: '404',
      component: () => import('@/pages/exception/404'),
    },
    {
      path: '/403',
      name: '403',
      component: () => import('@/pages/exception/403'),
    },
    {
      path: '/',
      name: '首页',
      component: TabsView,
      redirect: '/dashboard',
      children: [
        {
          path: 'addcompany',
          name: '加入企业',
          meta: {
            invisible: "true",
          },
          component: () => import('@/pages/personal/addCompany'),
        },
        {
          path: 'createcompany',
          name: '创建企业',
          meta: {
            invisible: "true"
          },
          component: () => import('@/pages/personal/createCompany'),
        },
        {
          path: 'notice',
          name: '消息中心',
          meta: {
            invisible: "true"
          },
          component: () => import('@/pages/personal/notice'),
        },
        {
            path: 'dashboard',
            name: '个人中心',
            meta: {
              icon: 'dashboard',
              showPageTitle:false
            },
            component: () => import('@/pages/personal/index.vue')
        },
        {
          path: 'meetingManagement',
          name: '会议管理',
          meta: {
            icon: 'table',
            page: {
              cacheAble: false
            }
          },
          component: BlankView,
          children: [
            {
              path: 'my',
              name: '我的会议',
              component: () => import('@/pages/meetingManagement/myMeeting/index'),
            },
            {
              path: 'history',
              name: '历史会议',
              component: () => import('@/pages/meetingManagement/historyMeeting/index'),
            },
            {
              path: 'attendee',
              name: '参会人列表',
              meta: {
                invisible: "true",
              },
              component: () => import('@/pages/meetingManagement/myMeeting/attendee')
            }
          ]
        },
        {
          path: 'reservedMeeting',
          name: '预约会议',
          meta: {
            icon: 'profile',
            page: {
              cacheAble: false
            }
          },
          component: () => import('@/pages/meetingReservation/reservedMeeting/index')
        },
        {
          path: 'meetingRoom',
          name: '预约会议室',
          meta: {
            icon: 'profile',
            page: {
              cacheAble: false
            },
            authority: {
              role: ['founder', 'admin', 'employee']
            }
          },
          component: () => import('@/pages/meetingReservation/meetingRoom/index.vue')
        },
        {
          path: 'changeReservedMeeting',
          name: '会议预约变更',
          meta: {
            icon: 'profile',
            page: {
              cacheAble: false
            },
            invisible: true//不渲染此路由至菜单项
          },
          component: () => import('@/pages/meetingReservation/changeReservedMeeting/index')
        },
        {
          path: 'changeMeetingRoom',
          name: '会议室预约变更',
          meta: {
            icon: 'profile',
            page: {
              cacheAble: false
            },
            authority: {
              role: ['founder', 'admin', 'employee']
            },
            invisible: true//不渲染此路由至菜单项
          },
          component: () => import('@/pages/meetingReservation/changeMeetingRoom/index')
        },
        {
          path: 'quickMeeting',
          name: '发起立即会议',
          meta: {
            icon: 'file-word',
            page: {
              cacheAble: false
            }
          },
          component: () => import('@/pages/quickMeeting/index')
        },
        {
          path: 'joinMeeting',
          name: '加入会议',
          meta: {
            icon: 'credit-card',
            page: {
              cacheAble: false
            }
          },
          component: () => import('@/pages/enterMeeting/index')
        },
        // {
        //   path: 'meetingOn',
        //   name: '会议进行中',
        //   meta: {
        //     icon: 'credit-card',
        //     page: {
        //       cacheAble: false
        //     },
        //     invisible: true
        //   },
        //   component: () => import('@/pages/meetingOn/index')
        // },
        {
          path: 'resourceManagement',
          name: '资源管理',
          meta: {
            icon: 'project',
            page: {
              cacheAble: false
            },
            authority: {
              role: ['founder', 'admin', 'employee']
            }
          },
          component: BlankView,
          children: [
            {
              path: 'information',
              name: '企业信息',
              meta: {
                icon: 'container'
              },
              component: () => import('@/pages/resourceManagement/information/index'),
            },
            {
              path: 'person',
              name: '企业用户',
              meta: {
                icon: 'idcard'
              },
              component: () => import('@/pages/resourceManagement/person/index'),
            },
            {
              path: 'invite',
              name: '邀请加入企业',
              meta: {
                invisible: "true",
              },
              component: () => import('@/pages/resourceManagement/person/invite'),
            },
            {
              path: 'manager',
              name: '添加管理员',
              meta: {
                invisible: "true"
              },
              component: () => import('@/pages/resourceManagement/manager/index'),
            },
            {
              path: 'room',
              name: '会议室',
              meta: {
                icon: "bank"
              },
              component: () => import('@/pages/resourceManagement/room/index'),
            },

          ]
          // component: PageView,
          // children: [
          //   {
          //     path: 'basic',
          //     name: '基础表单',
          //     component: () => import('@/pages/form/basic'),
          //   },
          //   {
          //     path: 'step',
          //     name: '分步表单',
          //     component: () => import('@/pages/form/step'),
          //   },
          //   {
          //     path: 'advance',
          //     name: '高级表单',
          //     component: () => import('@/pages/form/advance'),
          //   }
          // ]
        },
        // {
        //   path: 'form',
        //   name: '表单页',
        //   meta: {
        //     icon: 'form',
        //     page: {
        //       cacheAble: false
        //     }
        //   },
        //   component: PageView,
        //   children: [
        //     {
        //       path: 'basic',
        //       name: '基础表单',
        //       component: () => import('@/pages/form/basic'),
        //     },
        //     {
        //       path: 'step',
        //       name: '分步表单',
        //       component: () => import('@/pages/form/step'),
        //     },
        //     {
        //       path: 'advance',
        //       name: '高级表单',
        //       component: () => import('@/pages/form/advance'),
        //     }
        //   ]
        // },
        // {
        //   path: 'list',
        //   name: '列表页',
        //   meta: {
        //     icon: 'table'
        //   },
        //   component: PageView,
        //   children: [
        //     {
        //       path: 'query',
        //       name: '查询表格',
        //       meta: {
        //         authority: 'queryForm',
        //       },
        //       component: () => import('@/pages/list/QueryList'),
        //     },
        //     {
        //       path: 'query/detail/:id',
        //       name: '查询详情',
        //       meta: {
        //         highlight: '/list/query',
        //         invisible: true
        //       },
        //       component: () => import('@/pages/Demo')
        //     },
        //     {
        //       path: 'primary',
        //       name: '标准列表',
        //       component: () => import('@/pages/list/StandardList'),
        //     },
        //     {
        //       path: 'card',
        //       name: '卡片列表',
        //       component: () => import('@/pages/list/CardList'),
        //     },
        //     {
        //       path: 'search',
        //       name: '搜索列表',
        //       component: () => import('@/pages/list/search/SearchLayout'),
        //       children: [
        //         {
        //           path: 'article',
        //           name: '文章',
        //           component: () => import('@/pages/list/search/ArticleList'),
        //         },
        //         {
        //           path: 'application',
        //           name: '应用',
        //           component: () => import('@/pages/list/search/ApplicationList'),
        //         },
        //         {
        //           path: 'project',
        //           name: '项目',
        //           component: () => import('@/pages/list/search/ProjectList'),
        //         }
        //       ]
        //     }
        //   ]
        // },
        // {
        //   path: 'details',
        //   name: '详情页',
        //   meta: {
        //     icon: 'profile'
        //   },
        //   component: BlankView,
        //   children: [
        //     {
        //       path: 'basic',
        //       name: '基础详情页',
        //       component: () => import('@/pages/detail/BasicDetail')
        //     },
        //     {
        //       path: 'advance',
        //       name: '高级详情页',
        //       component: () => import('@/pages/detail/AdvancedDetail')
        //     }
        //   ]
        // },
        // {
        //   path: 'result',
        //   name: '结果页',
        //   meta: {
        //     icon: 'check-circle-o',
        //   },
        //   component: PageView,
        //   children: [
        //     {
        //       path: 'success',
        //       name: '成功',
        //       component: () => import('@/pages/result/Success')
        //     },
        //     {
        //       path: 'error',
        //       name: '失败',
        //       component: () => import('@/pages/result/Error')
        //     }
        //   ]
        // },
        // {
        //   path: 'exception',
        //   name: '异常页',
        //   meta: {
        //     icon: 'warning',
        //   },
        //   component: BlankView,
        //   children: [
        //     {
        //       path: '404',
        //       name: 'Exp404',
        //       component: () => import('@/pages/exception/404')
        //     },
        //     {
        //       path: '403',
        //       name: 'Exp403',
        //       component: () => import('@/pages/exception/403')
        //     },
        //     {
        //       path: '500',
        //       name: 'Exp500',
        //       component: () => import('@/pages/exception/500')
        //     }
        //   ]
        // },
        // {
        //   path: 'components',
        //   name: '内置组件',
        //   meta: {
        //     icon: 'appstore-o'
        //   },
        //   component: PageView,
        //   children: [
        //     {
        //       path: 'taskCard',
        //       name: '任务卡片',
        //       component: () => import('@/pages/components/TaskCard')
        //     },
        //     {
        //       path: 'palette',
        //       name: '颜色复选框',
        //       component: () => import('@/pages/components/Palette')
        //     },
        //     {
        //       path: 'table',
        //       name: '高级表格',
        //       component: () => import('@/pages/components/table')
        //     }
        //   ]
        // },
        // {
        //   name: '验权表单',
        //   path: 'auth/form',
        //   meta: {
        //     icon: 'file-excel',
        //     authority: {
        //       permission: 'form'
        //     }
        //   },
        //   component: () => import('@/pages/form/basic')
        // },
        // {
        //   name: '带参菜单',
        //   path: 'router/query',
        //   meta: {
        //     icon: 'project',
        //     query: {
        //       name: '菜单默认参数'
        //     }
        //   },
        //   component: () => import('@/pages/Demo')
        // },
        // {
        //   name: '动态路由菜单',
        //   path: 'router/dynamic/:id',
        //   meta: {
        //     icon: 'project',
        //     params: {
        //       id: 123
        //     }
        //   },
        //   component: () => import('@/pages/Demo')
        // },
        // {
        //   name: 'Ant Design Vue',
        //   path: 'antdv',
        //   meta: {
        //     icon: 'ant-design',
        //     link: 'https://www.antdv.com/docs/vue/introduce-cn/'
        //   }
        // },
        // {
        //   name: '使用文档',
        //   path: 'document',
        //   meta: {
        //     icon: 'file-word',
        //     link: 'https://iczer.gitee.io/vue-antd-admin-docs/'
        //   }
        // }
      ]
    },
    {
      path: 'meetingOn',
      name: 'meetingOn',
      meta: {
        icon: 'credit-card',
        page: {
          cacheAble: false
        },
      },
      component: () => import('@/pages/meetingOn/index')
    },
    {
      path: 'roomMeetingOn',
      name: 'roomMeetingOn',
      meta: {
        icon: 'credit-card',
        page: {
          cacheAble: false
        },
      },
      component: () => import('@/pages/meetingOn/roomIndex')
    },

  ]
}

export default options
