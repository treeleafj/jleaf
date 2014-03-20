
//首页菜单
Ext.define('jleaf.shop.SettingsMenu',{

	extend : 'Ext.ux.ContextMenu',
	
	config : {
		padding : 20,
		scrollable : true,
		defaults : {
			xtype : 'button',
			cls : 'demobtn',
			margin : '10 0'
		}
	},
	
	createItem : function(){
		
		return [{
			text: '个人信息',
			iconCls: 'info',
			handler : function(){
				Ext.Viewport.hideMenu('right');
			}
		},
		{
			text: '购物车',
			iconCls: 'compose',
			handler : function(){
				Ext.Viewport.toggleMenu('right');
			}
		},
		{
			text: '退出',
			style : 'background:red',
			iconCls: 'reply',
			handler : function(){
				delCookie(GlobalConfig.cookieAuthName);
				GlobalConfig._auth = null;
				window.location.reload();
			}
		}];
	},
	
	constructor: function (config) {
		if(!config){
			config = {};
		}
		config.items = this.createItem();
		this.callParent([config]);
    }
});