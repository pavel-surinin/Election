function getDistrict(self) {
  axios
    .get('district/representative')
    .then(function(response){
      self.setState({
        info :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('MultiResultContainer.componentWillMount.axios.get.district', err);
    });
}

var RepHomeContainer = React.createClass({
  getInitialState: function() {
    return {
      info : [],
      district : 5,
    };
  },
  componentWillMount: function() {
    getDistrict(this);
  },
  componentDidMount : function(){
    getDistrict(this);
  },
  render: function() {
    return (
      <RepHomeComponent info={this.state.info}/>
    );
  }

});

window.RepHomeContainer = RepHomeContainer;
