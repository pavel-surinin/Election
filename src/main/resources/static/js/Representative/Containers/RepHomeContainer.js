var RepHomeContainer = React.createClass({
  getInitialState: function() {
    return {
      info : [],
      district : 5,
    };
  },
  componentWillMount: function() {
    console.log('cwm');
    var self = this;
    axios
    .get('district/' + this.state.district)
    .then(function(response){
      self.setState({info : response.data});
    })
    .catch(function(err) {
      console.error(err);
    });
  },
  render: function() {
    return (
      <RepHomeComponent info={this.state.info}/>
    );
  }

});

window.RepHomeContainer = RepHomeContainer;
