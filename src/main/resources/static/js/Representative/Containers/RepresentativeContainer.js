var RepresentativeContainer = React.createClass({

  render: function() {
    return (
      <RepresentativeComponent>
        {this.props.children}
      </RepresentativeComponent>
    );
  }

});

window.RepresentativeContainer = RepresentativeContainer;
