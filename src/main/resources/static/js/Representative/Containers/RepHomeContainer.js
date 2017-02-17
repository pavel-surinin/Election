function getDistrictIdLogged(self) {
  axios
    .get('/users/logged/district')
    .then(function(response){
      self.setState({
        district :  response.data,
      });
    })
    .catch(function(err){
      console.error('componentWillMount.axios.get.districtId', err);
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
    var self = this;
    axios.get('/users/logged/district').then(function(r){
      self.setState({district : r.data});
      console.log('aaaaaaaaaaaaaaa',r.data);
    });
  },
  componentDidMount : function(){
    console.log(this);
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
