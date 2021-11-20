const koa = require('koa');
const fs = require('fs');
const mount = require('koa-mount');
const fstatic = require('koa-static');
const ejs = require('ejs');



const app = new koa();
const editorPageTemplate = fs.readFileSync(__dirname + '/index.html', 'utf-8') ;

app.use(
    fstatic(__dirname + '/resource/')
);

app.use(
    mount('/news', async (ctx) => {
        let category = 'NEWS EDITOR'
        let subtitle = '角度不同，真相当然不止一个。'
        let slogan = '轮到你为你在乎的人描述这个世界了'
        let clickEvent = 'publishToNews()'
        ctx.body = ejs.render(editorPageTemplate, {
                category: category,
                subtitle: subtitle,
                slogan: slogan,
                clickEvent: clickEvent
            }, {async: false}
        )
    })
);


app.use(
    mount('/blog', async (ctx) => {
        let category = 'BLOG EDITOR'
        let subtitle = '一起搞点事情'
        let slogan = '每一个突发奇想都值得被认真对待'
        let clickEvent = 'publishToSth()'
        ctx.body = ejs.render(editorPageTemplate, {
            category: category,
            subtitle: subtitle,
            slogan: slogan,
            clickEvent: clickEvent
        }, {async: false})
    })
);

module.exports = app;
