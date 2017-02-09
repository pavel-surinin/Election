function getDistrict(self) {
  axios
    .get('/district')
    .then(function(response){
      EventEmitter.publish({ eventType: 'LogCounty' });
      self.setState({
        districtList :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('DistrictListContainer.getDistrict.axios.get.district', err);
    });
}
var DistrictListContainer = React.createClass({
  getInitialState: function() {
    return {
      districtList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {

    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    getDistrict(this);
  },

  onHandleDelete: function(i) {
    var self = this;
    event.preventDefault();
    axios
      .delete('/district/'+ i)
      .then(function(response){
        getDistrict(self);
      })
      .catch(function(err){
        console.error('DistrictListContainer.onHandleDelete.axios', err);
      });
    },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      console.log(this.state.districtList);
      return (

        <DistrictListViewComponent
          districtList={this.state.districtList}
          onHandleDelete={this.onHandleDelete}
          succesCreateText={this.state.succesCreateText}
          />
      );
    }

  }

});

window.DistrictListContainer = DistrictListContainer;
