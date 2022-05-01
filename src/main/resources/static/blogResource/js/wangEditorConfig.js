<!--c初始化富文本编辑器 -->
const E = window.wangEditor;


// import  {message} from 'antd';
const editor = new E("#toolbar-container", "#text-container")
// 或者 const editor = new E(document.getElementById('div1'))
// 默认情况下，只有 IE 和 旧版 Edge 会使用兼容模式，
// 如果需要在其它浏览器上也使用兼容模式，
// 可以在函数内进行判定
editor.config.compatibleMode = function (){
    // 返回 true 表示使用兼容模式；返回 false 使用标准模式
    var ie = navigator.userAgent.indexOf("MSIE")>-1;
    if(ie){return true;}
    else  return  false;
}


//兼容模式可以自定义记录频率
editor.config.onchangeTimeout= 500;
editor.config.pasteTextHandle = false;
//插入链接校验
editor.config.linkImgCheck  = function (imgSrc,alt,href){
    return true;
}
//粘贴校验

//菜单编辑
editor.config.excludeMenus= [
    'backColor',
    'table'
]
editor.placeholder="写句话吧......";
//挂载highlight

editor.highlight=hljs;

editor.create();

// editor.config.customAlert = function(s,t){
//     switch (t) {
//         case 'success':
//             message.success(s)
//             break
//         case 'info':
//             message.info(s)
//             break
//         case 'warning':
//             message.warning(s)
//             break
//         case 'error':
//             message.error(s)
//             break
//         default:
//             message.info(s)
//             break
// }