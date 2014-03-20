//定义首页DataView商品数据
Ext.define('jleaf.shop.IndexDataView',{
	extend : 'Ext.DataView',
	config : {
		scrollable : true,
		inline : true,
		style : 'background:#F0F0F0',
		store : {
			autoLoad : true,
			params : {
				limit : 60
			},
			fields: [
	            {name: 'id', type: 'string'},
	            {name: 'name',  type: 'string'},
	            {name: 'price',       type: 'double'},
	            {name: 'img',  type: 'string'},
	            {name: 'count',  type: 'int'},
	            {name: 'introduction',  type: 'string'}
        	],
			proxy : {
				type : 'ajax',
				url : 'product',
				reader : {
					type : 'json',
					rootProperty : 'result'
				}
			}
		},
		itemTpl : '<div style="margin:15px;border:solid 2px #000"><img style="width:140px;height:130px;" src="images/product/{img}" alt="{introduction}">' +
				'<p style="text-align:center;background:#000;color:#F5F5F5;line-height:20px;">{name}</p></div>',
		listeners : {
			scope : this,
			itemsingletap : function(obj,index,target,record,e,eOpts){
				if(!this.productPanel){
					this.productPanel = Ext.create('jleaf.shop.ProductPanel');
					Ext.Viewport.add(this.productPanel);	
				}
				this.productPanel.updateProduct(record);
				this.productPanel.show();
			}
		}
	}
});

//首页标题栏
Ext.define('jleaf.shop.IndexTitleBar',{
	
	extend : 'Ext.TitleBar',
	config : {
		docked : 'top',
    	title : '商品首页',
    	logined : false
	},
	createItem : function(){
		return [
			{
    			iconCls : 'refresh',
    			align : 'left',
    			scope : this,
    			handler : function(btn){
    				GlobalConfig.productView.getStore().load();
    			}
    		},
    		{
    			iconCls : 'add',
    			text : '登录',
    			scope : this,
    			align : 'right',
    			handler : function(btn){
    				//如果已经登录就不要显示登录按钮,改为'设置'按钮
    				if(this.getLogined() == false){
	    				if(!this.loginPanel){
	    					this.loginPanel = Ext.create('jleaf.shop.LoginPanel',{
	    						listeners : {
	    							scope : this,
		    						loginSuccess : function(form,data){
		    							
		    							var authId = data.result.id;
		    							//设置Cookie
		    							var time = new Date();
		    							time.setTime(time.getTime() + (3 * 24 * 3600 * 1000));//3天有效期
		    							setCookie(GlobalConfig.cookieAuthName,authId,time);
		    							//内存缓存
		    							GlobalConfig._auth = authId;
		    							//设置按钮等样式
		    							btn.setText('设置');
		    							form.reset();
		    							this.setLogined(true);
		    							btn.setIconCls('settings');
		    						}
	    						}
	    					});
	    					Ext.Viewport.add(this.loginPanel);
	    				}
	    				this.loginPanel.show();
    				}else{
    					this.fireEvent('tapSettings',this,btn);
    				}
    			},
    			listeners : {
    				scope : this,
    				activate : function(item,btn){
    					var _auth = getCookie(GlobalConfig.cookieAuthName);
    					if(_auth){
    						item.setText('设置');
    						item.setIconCls('settings');
    						this.setLogined(true);
    						GlobalConfig._auth = _auth;
    					}
		    		}
    			}
    		}
    	];
	},
	//构造函数
	constructor: function (config) {
		
		Ext.apply(config,{
			items : this.createItem()
		});
		
		Ext.apply(config.listeners,{
    		activate : function(){
    			alert('1');
    		}
		});
		
		this.on('tapSettings',function(){},this);
		
		this.callParent([config]);
    }
});