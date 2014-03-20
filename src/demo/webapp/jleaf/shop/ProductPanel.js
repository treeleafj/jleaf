//产品图片幻灯片
Ext.define('jleaf.shop.ProductImageCarousel',{
	extend : 'Ext.Carousel',
	config : {
		imgs : null,
		minWidth : 300,
		minHeight : 300,
		maxWidth : 550,
		maxHeight : 350,
		style : 'border:2px solid #B47A12;',
		defaults: {
	        styleHtmlContent: true
	    }
	},
	
	createItems : function(){
		var items = [];
		if(this.getImgs()){
			for(var i = 0; i < this.getImgs().length; i++){
				var img = this.getImgs()[i];
				items.push({
					html : '<img style="width:100%;height:100%;" src="' + img + '"/>'
				});
			}
		}
		return items;
	},
	
	//构造函数
	constructor: function (config) {
		if(!config){
			config = {};
		}
		
		this.callParent([config]);
		
		this.add(this.createItems());
		
	}
	
});

//产品主界面
Ext.define('jleaf.shop.ProductPanel',{
	
	extend : 'Ext.Container',
	
	config : {
		width : '100%',
		height : '100%',
		scrollable : true,
		modal : true,
		productData : null,
		centered : true,
		fullscreen : true,
		style : 'background:#F5F5F5',
		hideOnMaskTap : true,

		showAnimation : {
			type : 'slideIn',
			duration : 300,
			direction : 'down'
		},
		hideAnimation :{
			type : 'slideOut',
			duration : 250,
			direction : 'up'
		}
	},
	
	createItems : function(){
		return [
			{
				xtype : 'titlebar',
				docked : 'top',
				style : 'background:red',
    			title : '关闭',
    			listeners : { 
	    			scope : this,
    				initialize : function(obj){
    					var productPanel = this;
    					obj.element.on('tap',function(){
    						productPanel.hide();
    					});
    				}
    			}
			}
		];
	},
	
	createProductInfo : function(product){
		
		var imgCarousel = Ext.create('jleaf.shop.ProductImageCarousel',{
			flex : 1,
			imgs : ['images/product/1.jpg','images/product/2.jpg','images/product/3.jpg']//product.get('imgs')
		});
		
		
		var tabPanel = Ext.create('Ext.tab.Panel',{
			height : 300,
			items : [
				{
					xtype : 'container',
					title : '商品详情',
					style : 'background:#F0F0F0;color:#222;line-height:30px;text-align:center;',
					html : '<br /><p>价格: <font style="color:red">' + product.get('price') + '</font></p>'
						+ '<p>数量: <font style="color:red">' + product.get('count') + '</font></p>' 
						+ '<p>介绍: ' + product.get('introduction') + '</p>'
				},{
					title : '用户评价',
					style : 'background:#F0F0F0;color:#222;line-height:30px;text-indent:1em;',
					html : '暂无..'
				}
			]
			
		});
		
		return {
			xtype : 'container',
			style : 'background : #FEFEFE;',
			items : [
				{
					xtype : 'container',
					height : 350,
					style : 'padding:30px 0;',
					layout : {
						type : 'vbox',
						align: 'middle'
					},
					items : [imgCarousel]
				},
				{
					xtype : 'titlebar',
					width : '100%',
					style : 'background:#498ED3;',
					title : product.get('name')
				},
				tabPanel
			]
		}
	},
	
	updateProduct : function(product){
		var infoPanel = this.createProductInfo(product);
		if(this.getItems().length > 1){
			this.removeAt(1);
		}
		this.add(infoPanel);
		this.setProductData(product);
	},
		
	//构造函数
	constructor: function (config) {
		if(!config){
			config = {};
		}
		Ext.apply(config,{
			items : this.createItems()
		});
		this.callParent([config]);
	}
});