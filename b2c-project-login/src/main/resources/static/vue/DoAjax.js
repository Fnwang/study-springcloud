
/**
 * moxiaoming 2019-02-21
 * 定义DO类
 * 封装一些常用的方法，和业务无关，其他项目也可以使用
 * 含private_的方法是提供本类内部调用
 * 其他可通用调用
 */
var DO =
{
    TYPE_GET    : 'get',
    TYPE_POST   : 'post',
    TYPE_PUT    : 'put',
    TYPE_DELETE : 'delete',

    /**
     * 通用数据请求方法
     * @param vueObj
     * @param apiURL
     * @param callback
     * @param params
     * @param calltype
     */
    doData : function(vueObj,apiURL,callback,params,calltype)
    {
        //根据不同的调用方式
        switch (calltype || this.TYPE_GET)
        {
            //get 方式
            case this.TYPE_GET :
                vueObj.$http.get(apiURL, params).then( res => {this.private_docallback(res,callback,this.TYPE_GET)});
                break ;
            //post 方式 ,{emulateJSON:true}解决跨域
            case this.TYPE_POST :
                vueObj.$http.post(apiURL, params,{emulateJSON:true}).then( res => {this.private_docallback(res,callback,this.TYPE_POST)});
                break ;
            //put 方式,{emulateJSON:true}解决跨域
            case this.TYPE_PUT :
                vueObj.$http.put(apiURL, params,{emulateJSON:true}).then( res => {this.private_docallback(res,callback,this.TYPE_PUT)});
                break ;
            //delete方式,{emulateJSON:true}解决跨域
            case this.TYPE_DELETE :
                vueObj.$http.delete(apiURL, params,{emulateJSON:true}).then( res => {this.private_docallback(res,callback,this.TYPE_DELETE)});
                break ;
        };

    },

    /**
     * 通用回调方法
     * @param res
     * @param callback
     * @param calltype
     */
    private_docallback : function(res,callback,calltype) {
        // const rs = res.json();
        // callback(rs);
        const rs = res.json();
        //console.log('in callback:',rs);
        if( rs && rs.code == "200" && calltype==this.TYPE_GET) {
            //查询返回data，其他的不要
            callback(rs.data);
        }else if( rs && (calltype==this.TYPE_POST || calltype==this.TYPE_PUT || calltype==this.TYPE_DELETE)){
            ////增删改，返回整个rs对象
            callback(rs);
            //alert(rs.msg);
        }
    },
};