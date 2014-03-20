Ext.application({
    viewport: {
        autoMaximize: true // 设置页面自动最大化（隐藏地址栏）
    },
    launch: function () {
    	//定义格栅的图片显示
		GlobalConfig.productView = Ext.create('jleaf.shop.IndexDataView');
		
		GlobalConfig.titleBar = Ext.create('jleaf.shop.IndexTitleBar',{
			listeners : {
				//点击设置按钮时触发
				tapSettings : function(){
					if(GlobalConfig.settingMenu.isHidden()){
						Ext.Viewport.showMenu('right');
					}else{
						Ext.Viewport.hideMenu('right');
					}
				}
			}
		});
		
    	Ext.Viewport.add(GlobalConfig.productView);
        Ext.Viewport.add(GlobalConfig.titleBar);
			
		GlobalConfig.settingMenu = Ext.create('jleaf.shop.SettingsMenu');
		
	   	Ext.Viewport.setMenu(GlobalConfig.settingMenu,{
	  		side: 'right',
			reveal: true
	 	});
    }
});