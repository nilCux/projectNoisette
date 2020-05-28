const mount = require('koa-mount');
const koa = require('koa');
const url = require('url')

const app = new koa;

/**
 * 由于很多同学打开demo的时候习惯性没加最后的/
 * 导致页面在访问css或者js内容的时候，会因为拼错链接而返回404
 * 所以这里加上一个判断，没有以/结尾的时候要进行重定向。
 * 
 * 当然，在koa-mount进行路由匹配的时候直接匹配 /download/ 也是可以避免出现错误页面的
 * 但是为了用户考虑，最好就是帮助用户修正错误。
 */
app.use(async (ctx, next)=> {
    const parsedUrl = url.parse(ctx.url);
    if (
        (parsedUrl.pathname === '/register') || 
        (parsedUrl.pathname === '/login') || 
        (parsedUrl.pathname === '/play') || 
        (parsedUrl.pathname === '/list')
    ) {
        parsedUrl.pathname = parsedUrl.pathname + '/'
        ctx.redirect(url.format(parsedUrl));
        return
    }

    await next();
})

app.use(
    mount('/register', require('./registerPage/index'))
)

app.use(
    mount('/login', require('./loginPage/index'))
)
/*
app.use(
    mount('/play', require('./3.play/index'))
)
app.use(
    mount('/list', require('./4.list/node/index'))
)
*/

app.listen(3000, ()=> {
    console.log('listened 3000')
});