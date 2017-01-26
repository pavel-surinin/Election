var DistrictListContainer = React.createClass({
  getInitialState: function() {
    return {
      districtList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/district')
      .then(function(response){
        self.setState({
          districtList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('DistrictListContainer.componentWillMount.axios.get.district', err);
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
