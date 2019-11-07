function loadScrollToTop()
{if($("#backtop").attr("id")!="backtop")
{$("#query2").append('<p id="backtop"><a href="javascript:void(0)"><span></span>回顶部</a></p>')}$("#backtop").hide();
    $(function()
    {$(window).scroll(function()
    {if($(this).scrollTop()>200)
    {$("#backtop").fadeIn()}else
    {$("#backtop").fadeOut()}});
        $("#backtop a").click(function()
        {$("body,html").animate(
            {scrollTop:0},800);
            return false})})}
function loadScrollToBottom()
{if($("#backbottom").attr("id")!="backbottom")
{$("#query2").append('<p id="backbottom"><a href="javascript:void(0)"><span></span>到底部</a></p>')}$("#backbottom").show();
    if($(document).height()==$(window).height())
    {$("#backbottom").hide()}$(function()
{$(window).scroll(function()
{if($(this).scrollTop()<$(document).height()-$(window).height()-200)
{$("#backbottom").fadeIn()}else
{$("#backbottom").fadeOut()}});
    $("#backbottom a").click(function()
    {$("body,html").animate(
        {scrollTop:$(document).height()},800);
        return false})})}