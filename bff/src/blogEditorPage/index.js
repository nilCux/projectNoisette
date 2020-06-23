const koa = require('koa');
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');
const ejs = require('ejs');
const { brotliDecompressSync } = require('zlib');


const app = new koa();
const blogEditorPageContent = fs.readFileSync(__dirname + '/resource/index.html', 'utf-8') ;

//app.use()`
app.use(
    static(__dirname)
);

app.use(
    mount('/', async (ctx) => {
        let content = ejs.compile(blogEditorPageContent)
        let category = '一起搞点事情'
        let slogan ='每一个突发奇想都值得被认真对待'
        let body = ejs.render(blogEditorPageContent, {category: category, slogan:slogan}, {async: false})
        ctx.body = body
    })
);

module.exports = app;