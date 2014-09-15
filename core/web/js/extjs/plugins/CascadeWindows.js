Ext.namespace('Ext.ux.plugins');

Ext.ux.plugins.CascadeWindows = function(config) {
    Ext.apply(this, config);
};

Ext.extend(Ext.ux.plugins.CascadeWindows, Ext.util.Observable, {
    init:function(win) {
        Ext.apply(win, {
            onRender:win.onRender.createSequence(function(ct, position) {
            var mgr = (this.manager)?this.manager:Ext.WindowMgr;
            var ctr = this.container;
            var size = { width: this.width,
                 height: this.height };
            var ctSize = this.getEl().getAlignToXY(ctr.id, "tl-br");
            var offSetX = (this.offSetX)?this.offSetX:15;
            var offSetY = (this.offsetY)?this.offsetY:-15;
            if (mgr.cascaded) {
            mgr.cascaded += 1;
            } else {
            mgr.cascaded = 1;
            }
            if (mgr.cascaded == 1) {
            mgr.lastXY = this.getEl().getAlignToXY(ctr.id, "tl-tl?", [offSetX,offSetY]);
            } else {
            mgr.lastXY[0] += 40;
            mgr.lastXY[1] += 25;
            var testBR = {bottom: (size.height + mgr.lastXY[1]),
                      right: (size.width + mgr.lastXY[0]) };
            if (testBR.bottom > ctSize[1]) {
                mgr.lastXY[1] = 0;
            }
            if (testBR.right > ctSize[0]) {
                mgr.lastXY[0] = 15;
            }
            }
            this.cascaded = true;
            this.origPos = [];
            this.origPos[0] = mgr.lastXY[0];
            this.origPos[1] = mgr.lastXY[1];
            this.setPosition(mgr.lastXY[0], mgr.lastXY[1]);
        }),
        cascade: function(e) {
            this.setPosition(this.origPos[0], this.origPos[1]);
            }
       });
    } // end of function init
}); // end of extend  