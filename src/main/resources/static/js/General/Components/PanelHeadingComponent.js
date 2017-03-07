var PanelHeadingComponent = React.createClass({
  componentDidMout:function(){
    $(this.refs.title).click(function() {
      $(this.refs.icon).css({
        'transition': '0.4s',
        'transform': 'rotate(90deg)',
      });
    });
  },
  render: function() {
    return(
    <div className="panel-heading" role="tab" id="headingSix">
      <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
          href="#collapseSix" aria-expanded="false" aria-controls="collapseSix" ref='title' id='heading'>
        {this.props.title}
        <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
      </h4>
    </div>
    )}
});

window.PanelHeadingComponent = PanelHeadingComponent;