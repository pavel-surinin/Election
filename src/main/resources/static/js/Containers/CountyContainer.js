function getCounty(self) {
  axios
    .get('/county')
    .then(function(response){
      self.setState({
        countyList :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('CountyContainer.getCounty.axios.get.county', err);
    });
}

var CountyContainer = React.createClass({
  getInitialState: function() {
    return {
      countyList : [],
      isLoading : true,
    };
  },
  componentWillMount: function() {
    getCounty (this);
  },
    onHandleDelete: function(i) {
      var self = this;
      event.preventDefault();
      axios
        .delete('/county/'+ i)
        .then(function(response){
          getCounty(self);
        })
        .catch(function(err){
          console.error('CountyContainer.onHandleDelete.axios', err);
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
      console.log(this.state.countyList);
      return (
        <CountyListViewComponent
          countyList={this.state.countyList}
          onHandleDelete={this.onHandleDelete}
        />
      );
    }
  }
});

window.CountyContainer = CountyContainer;
