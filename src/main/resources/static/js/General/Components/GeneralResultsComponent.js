var GeneralresultsComponent = React.createClass({
  componentWillReceiveProps: function(nextProps) {
    if (nextProps.visible !== this.props.visible) {
      if (nextProps.visible) {
        $(findDOMNode(this)).stop( true, true ).fadeIn('slow');
      } else {
        $(findDOMNode(this)).stop( true, true ).fadeOut('slow');
      }
    }
  },
  render: function() {
    console.log('GeneralresultsComponent');
    console.log(this.props.results);
    return (
      <h1 className='yellow'>GeneralresultsComponent</h1>
    );
  }

});

window.GeneralresultsComponent = GeneralresultsComponent;
