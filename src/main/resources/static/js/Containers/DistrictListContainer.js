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

  componentWillMount: function() {
    getDistrict(this);
  },

  onHandleDeleteClick: function(districtId) {
    var self1= this;
    var self2= this.props;
    var self3= this.props.params;
    console.log("0"+id);
    console.log("1"+this.id);
    console.log("2"+this.props.id);
    console.log("3"+this.props.params.id);
    console.log("4"+self1.id);
    console.log("5"+self2.id);
    console.log("6"+<DistrictListViewRowComponent id={this.props.id} />);
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
<<<<<<< HEAD
        <DistrictListViewComponent districtList={this.state.districtList}
          onHandleDeleteClick={this.onHandleDeleteClick}
        />
=======
        <DistrictListViewComponent
          districtList={this.state.districtList}
          onHandleDelete={this.onHandleDelete}
          />
>>>>>>> d530673c786ec1d06cc74d161486ef901500424e
      );
    }

  }

});

window.DistrictListContainer = DistrictListContainer;
