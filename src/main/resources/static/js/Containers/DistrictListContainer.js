function getDistrict(self) {
  axios
    .get('/district')
    .then(function(response){
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

  onHandleDelete : function(i){
  },

  componentWillMount: function() {
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
          <img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/>
        </div>
      );
    } else {
      console.log(this.state.districtList);
      return (

        <DistrictListViewComponent
          districtList={this.state.districtList}
          onHandleDelete={this.onHandleDelete}
          />
      );
    }

  }

});

window.DistrictListContainer = DistrictListContainer;
